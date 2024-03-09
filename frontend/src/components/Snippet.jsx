import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import {IconButton} from "@mui/material";
import {useNavigate} from "react-router-dom";

const Snippet = ({snippet, deleteSnippet}) => {
    const navigate = useNavigate();

    return (
        <div className={"snippet"}>
            <h1>Snippet</h1>
            <p>Author: {snippet.user.username}</p>
            <p>Date: {snippet.date}</p>
            <pre className={"snippet_code"}>
                <code>{snippet.code}</code>
            </pre>
            {deleteSnippet !== undefined && (
                <>
                    <IconButton aria-label="edit" onClick={() => navigate("/edit", { state: { snippet: snippet } })}>
                        <EditIcon color={"primary"} />
                    </IconButton>
                    <IconButton aria-label="delete" onClick={() => deleteSnippet(snippet.id)}>
                        <DeleteIcon color={"error"} />
                    </IconButton>
                </>
            )}
        </div>

    )
}
export default Snippet;