import React, { useState } from "react";
import MyButton from "../components/UI/button/MyButton";
import { useNavigate, useLocation } from "react-router-dom";
import { editSnippet } from "../services/SnippetService";

const EditPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const snippet = location.state?.snippet;

  const [snippetText, setSnippetText] = useState(snippet ? snippet.code : "");
  const [commentError, setCommentError] = useState("");

  const handleEditSnippet = async (snippet) => {
    try {
      const result = await editSnippet(snippet);
      console.log(result);
    } catch (error) {
      console.error("Error in handleEditSnippet", error);
      throw error;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!snippetText.trim()) {
      setCommentError("Snippet cannot be empty.");
      return;
    } else {
      setCommentError("");
    }

    try {
      await handleEditSnippet({ ...snippet, code: snippetText });
      navigate("/home");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className={"newSnippet"}>
      <h2>Edit snippet</h2>
      {commentError && (
        <div style={{ color: "red", textAlign: "center", marginBottom: "5px" }}>
          {commentError}
        </div>
      )}{" "}
      <form onSubmit={handleSubmit}>
        <textarea
          id="myInput"
          placeholder="Enter your snippet"
          value={snippetText}
          onChange={(e) => setSnippetText(e.target.value)}
        ></textarea>
        <MyButton value={"Submit"} type="submit" />
      </form>
      <MyButton onClick={() => navigate("/home")} value={"Back to home"} />
    </div>
  );
};

export default EditPage;
