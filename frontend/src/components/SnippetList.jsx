import Snippet from "./Snippet";
import React from "react";

const SnippetList = ({ username, snippets, editSnippet, deleteSnippet }) => {
  return (
    <div className={"snippetList"}>
      {snippets.map((s) => {
        if (s.user.username === username) {
          return (
            <Snippet
              key={s.id}
              snippet={s}
              editSnippet={editSnippet}
              deleteSnippet={deleteSnippet}
            ></Snippet>
          );
        } else {
          return <Snippet key={s.id} snippet={s}></Snippet>;
        }
      })}
    </div>
  );
};

export default SnippetList;
