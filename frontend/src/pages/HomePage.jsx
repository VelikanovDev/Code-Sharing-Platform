import React from 'react';
import SnippetList from "../components/SnippetList";
import AdminActionButtons from "../components/AdminActionButtons";
import UserActionButtons from "../components/UserActionButtons";

const HomePage = ({ username, role, snippetList, deleteSnippet, deleteAll, logout }) => {
    return (
        <div>
            <h2>Username: {username}</h2>
            <h2>Role: {role}</h2>
            {role === 'ADMIN' && (
                <AdminActionButtons deleteAll={deleteAll} logout={logout} />
            )}
            {role === 'USER' && (
                <UserActionButtons logout={logout}/>
            )}
            <hr />
            {snippetList.length > 0 ? (
                <SnippetList username={username} role={role} snippets={snippetList} deleteSnippet={deleteSnippet} />
            ) : (
                <p>There are no snippets yet. Be the first one!</p>
            )}
        </div>
    );
};

export default HomePage;
