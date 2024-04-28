import CommentIcon from "@mui/icons-material/Comment";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import { IconButton, Rating } from "@mui/material";
import { useNavigate } from "react-router-dom";
import SyntaxHighlighter from "react-syntax-highlighter";
import { docco } from "react-syntax-highlighter/dist/esm/styles/hljs";
import React, { useEffect, useState } from "react";
import ClearIcon from "@mui/icons-material/Clear";
import MyButton from "./UI/button/MyButton";
import { useAuth } from "../provider/AuthProvider";
import { addRating, getAverageRating } from "../services/SnippetService";

const Snippet = ({ snippet, deleteSnippet, addComment, deleteComment }) => {
  const [comments, setComments] = useState(snippet.comments);
  const [showComments, setShowComments] = useState(false);
  const [commentText, setCommentText] = useState("");
  const [commentError, setCommentError] = useState("");
  const [averageRating, setAverageRating] = useState(null);
  const navigate = useNavigate();
  const { userDetails } = useAuth();

  useEffect(() => {
    const fetchRating = async () => {
      try {
        const rating = await getAverageRating(snippet.id);
        setAverageRating(rating);
      } catch (error) {
        console.error("Error fetching rating:", error);
      }
    };
    fetchRating();
  }, [averageRating, snippet.id, userDetails.username]); // Call the effect whenever the snippet id changes

  const handleAddComment = async (e) => {
    e.preventDefault();
    if (!commentText.trim()) {
      setCommentError("Comment cannot be empty.");
      return;
    } else {
      setCommentError("");
    }

    const newComment = await addComment(snippet.id, commentText);

    setComments([...comments, newComment]);
    setCommentText("");
  };

  const handleDeleteComment = async (commentId) => {
    await deleteComment(commentId);
    setComments(comments.filter((c) => c.id !== commentId));
  };

  const handleAddRating = async (newValue) => {
    if (newValue !== null) {
      try {
        // Add the rating to the backend
        await addRating(newValue, userDetails.username, snippet.id);

        // Update the average rating in the UI
        const newAverageRating = await getAverageRating(snippet.id);
        setAverageRating(newAverageRating);
      } catch (error) {
        console.error("Error adding rating:", error);
      }
    }
  };

  return (
    <div className={"snippet"}>
      <h3>Author: {snippet.user.username}</h3>
      <h3>Date: {snippet.date}</h3>
      {snippet.editDate == null ? null : <h3>Edited at: {snippet.editDate}</h3>}
      <pre className={"snippet_code"}>
        <SyntaxHighlighter language="javascript" style={docco}>
          {snippet.code}
        </SyntaxHighlighter>
      </pre>
      <div className="container">
        <div className={"rating-section"}>
          <Rating
            name="no-value"
            precision={0.5}
            value={averageRating}
            onChange={(event, newValue) => {
              handleAddRating(newValue);
            }}
          />
          {averageRating ? averageRating.toFixed(1) : "0"}
        </div>
        <div className={"buttons-section"}>
          <IconButton
            aria-label="comment"
            onClick={() => setShowComments(!showComments)}
          >
            <CommentIcon color={"primary"} />
          </IconButton>

          {snippet.user.username === userDetails.username && (
            <>
              <IconButton
                aria-label="edit"
                onClick={() =>
                  navigate("/edit", { state: { snippet: snippet } })
                }
              >
                <EditIcon color={"primary"} />
              </IconButton>
              <IconButton
                aria-label="delete"
                onClick={() => deleteSnippet(snippet.id)}
              >
                <DeleteIcon color={"error"} />
              </IconButton>
            </>
          )}

          {userDetails.role === "ADMIN" &&
            snippet.user.username !== userDetails.username && (
              <>
                <IconButton
                  aria-label="delete"
                  onClick={() => deleteSnippet(snippet.id)}
                >
                  <DeleteIcon color={"error"} />
                </IconButton>
              </>
            )}
        </div>
      </div>
      {showComments && (
        <div className="comment-section">
          <h4>Comments</h4>

          {comments && comments.length > 0 ? (
            comments.map((comment) => (
              <div key={comment.id} className="comment">
                <h5>Author: {comment.username}</h5>
                <h5>Date: {comment.date}</h5>
                <p>{comment.text}</p>
                {(comment.username === userDetails.username ||
                  userDetails.role === "ADMIN") && (
                  <IconButton
                    aria-label="delete"
                    onClick={() => handleDeleteComment(comment.id)}
                    style={{
                      position: "absolute",
                      right: "10px",
                      top: "10px",
                    }}
                  >
                    <ClearIcon color={"error"} />
                  </IconButton>
                )}
              </div>
            ))
          ) : (
            <p>No comments yet!</p>
          )}
          <form onSubmit={handleAddComment}>
            <div
              style={{ color: "red", textAlign: "center", marginBottom: "5px" }}
            >
              {commentError}
            </div>
            <textarea
              id="commentInput"
              placeholder="Enter your comment"
              value={commentText}
              onChange={(e) => setCommentText(e.target.value)}
            ></textarea>
            <MyButton value={"Submit"} type="submit" />
          </form>
        </div>
      )}
    </div>
  );
};
export default Snippet;
