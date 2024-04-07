import React from "react";
import SnippetList from "../components/SnippetList";
import { IconButton } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { useNavigate } from "react-router-dom";
const HomePage = (props) => {
  const navigate = useNavigate();
  if (props.isLoading) {
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
      {props.snippetList.length > 0 ? (
        <SnippetList
          username={props.username}
          role={props.role}
          snippets={props.snippetList}
          deleteSnippet={props.deleteSnippet}
          addComment={props.addComment}
          deleteComment={props.deleteComment}
        />
      ) : (
        <p>There are no snippets yet. Be the first one!</p>
      )}
    </div>
  );
};

export default HomePage;
