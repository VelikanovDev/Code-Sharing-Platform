import React from "react";
import AccountMenu from "./AccountMenu";

const AppHeader = ({ isLoggedIn, handleDeleteAll, handleLogout }) => {
  return (
    <header className="AppHeader">
      {isLoggedIn && (
        <div className={"AccountPanel"}>
          <AccountMenu
            username={localStorage.getItem("username")}
            deleteAll={handleDeleteAll}
            logout={handleLogout}
          />
        </div>
      )}
      <h1>Code-Sharing Platform</h1>
    </header>
  );
};

export default AppHeader;
