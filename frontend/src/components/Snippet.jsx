import CommentIcon from "@mui/icons-material/Comment";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import { IconButton } from "@mui/material";
import { useNavigate } from "react-router-dom";
import SyntaxHighlighter from "react-syntax-highlighter";
import { docco } from "react-syntax-highlighter/dist/esm/styles/hljs";

const Snippet = ({ snippet, deleteSnippet }) => {
  const navigate = useNavigate();

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
      {deleteSnippet !== undefined && (
        <div style={{ display: "flex", justifyContent: "flex-end" }}>
          <IconButton
            aria-label="comment"
            onClick={() => console.log("comment click")}
          >
            <CommentIcon color={"primary"} />
          </IconButton>

          <IconButton
            aria-label="edit"
            onClick={() => navigate("/edit", { state: { snippet: snippet } })}
          >
            <EditIcon color={"primary"} />
          </IconButton>
          <IconButton
            aria-label="delete"
            onClick={() => deleteSnippet(snippet.id)}
          >
            <DeleteIcon color={"error"} />
          </IconButton>
        </div>
      )}
    </div>
  );
};
export default Snippet;
