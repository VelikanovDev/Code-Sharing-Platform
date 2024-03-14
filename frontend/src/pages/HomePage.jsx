import React from "react";
import SnippetList from "../components/SnippetList";
import { IconButton } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { useNavigate } from "react-router-dom";
const HomePage = ({ username, role, snippetList, deleteSnippet }) => {
  const navigate = useNavigate();
  return (
    <div className={"homePage"}>
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
      {snippetList.length > 0 ? (
        <SnippetList
          username={username}
          role={role}
          snippets={snippetList}
          deleteSnippet={deleteSnippet}
        />
      ) : (
        <p>There are no snippets yet. Be the first one!</p>
      )}
    </div>
  );
};

export default HomePage;
