import React, { useState } from "react";
import MyButton from "../components/UI/button/MyButton";
import { useNavigate, useLocation } from "react-router-dom";

const EditPage = ({ editSnippet }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const snippet = location.state?.snippet;

  const [snippetText, setSnippetText] = useState(snippet ? snippet.code : "");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await editSnippet({ ...snippet, code: snippetText });
      navigate("/home");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className={"newSnippet"}>
      <h2>Edit snippet</h2>
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
