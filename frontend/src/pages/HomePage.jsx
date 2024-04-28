import React, { useEffect, useState } from "react";
import SnippetList from "../components/SnippetList";
import { IconButton } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { useNavigate } from "react-router-dom";
import {
  addComment,
  deleteComment,
  deleteSnippet,
  fetchSnippets,
} from "../services/SnippetService";
import { useAuth } from "../provider/AuthProvider";

const HomePage = () => {
  const navigate = useNavigate();
  const { userDetails } = useAuth();
  const [snippets, setSnippets] = useState([]);
  const [refreshSnippets, setRefreshSnippets] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    fetchSnippets()
      .then((res) => {
        setSnippets(res);
        setRefreshSnippets(false);
      })
      .catch((err) => {
        console.error("Failed to fetch snippets:", err);
        if (err.response && err.response.status === 401) {
          handleLogout();
        }
      })
      .finally(() => setIsLoading(false));
  }, [refreshSnippets]);

  const handleLogout = () => {
    localStorage.clear();
  };

  const handleDeleteSnippet = async (snippetId) => {
    try {
      await deleteSnippet(snippetId);
      setRefreshSnippets(true);
    } catch (error) {
      console.error("Error in handleDeleteSnippet", error);
      throw error;
    }
  };

  const handleAddComment = async (snippetId, commentText) => {
    try {
      return await addComment(userDetails.username, snippetId, commentText);
    } catch (error) {
      console.error("Error in handleAddComment", error);
      throw error;
    }
  };

  const handleDeleteComment = async (commentId) => {
    try {
      await deleteComment(commentId);
    } catch (error) {
      console.error("Error in handleDeleteComment", error);
      throw error;
    }
  };

  if (isLoading) {
    return <p>Snippets are loading...</p>;
  }

  return (
    <div className={"homePage"}>
      <div className={"headerContainer"}>
        <h2 className={"allSnippetsHeader"}>All snippets</h2>
        <div className={"addButton"}>
          <IconButton
            aria-label="add"
            onClick={() => {
              navigate("/new");
            }}
            sx={{ fontSize: 30 }}
          >
            <AddCircleIcon color={"primary"} sx={{ fontSize: 30 }} />
          </IconButton>
        </div>
      </div>
      {snippets.length > 0 ? (
        <SnippetList
          snippets={snippets}
          deleteSnippet={handleDeleteSnippet}
          addComment={handleAddComment}
          deleteComment={handleDeleteComment}
        />
      ) : (
        <p>There are no snippets yet. Be the first one!</p>
      )}
    </div>
  );
};

export default HomePage;
