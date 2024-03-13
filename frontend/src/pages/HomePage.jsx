import React from "react";
import SnippetList from "../components/SnippetList";
import AdminActionButtons from "../components/AdminActionButtons";
import UserActionButtons from "../components/UserActionButtons";
import { IconButton } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { useNavigate } from "react-router-dom";
const HomePage = ({ username, role, snippetList, deleteSnippet }) => {
  const navigate = useNavigate();
  return (
    <div>
      {/*<AccountPanel className={"accountPanel"} username={username} />*/}
      {/*<h2 id="username">Username: {username}</h2>*/}
      {/*<h2 id="role">Role: {role}</h2>*/}
      {/*{role === "ADMIN" && (*/}
      {/*  <AdminActionButtons deleteAll={deleteAll} logout={logout} />*/}
      {/*)}*/}
      {/*{role === "USER" && <UserActionButtons logout={logout} />}*/}
      <hr />
      <IconButton
        aria-label="add"
        onClick={() => {
          navigate("/new");
        }}
      >
        <AddCircleIcon color={"primary"} sx={{ fontSize: 16 }} />
      </IconButton>
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
