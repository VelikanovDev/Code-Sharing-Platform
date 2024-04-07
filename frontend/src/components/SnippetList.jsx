import Snippet from "./Snippet";
import React from "react";

const SnippetList = ({
  username,
  role,
  snippets,
  deleteSnippet,
  addComment,
  deleteComment,
}) => {
  return (
    <div className={"snippetList"}>
      {snippets.map((s) => {
        return (
          <Snippet
            key={s.id}
            username={username}
            role={role}
            snippet={s}
            deleteSnippet={deleteSnippet}
            addComment={addComment}
            deleteComment={deleteComment}
          ></Snippet>
        );
      })}
    </div>
  );
};

export default SnippetList;
