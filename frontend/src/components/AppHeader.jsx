import React from "react";
import AccountMenu from "./AccountMenu";

const AppHeader = ({ isLoggedIn, username, role, deleteAll, logout }) => {
  return (
    <>
      <header className="AppHeader">
        {/* Invisible div for balancing */}
        <div className="Placeholder"></div>

        <div className="TitleContainer">
          <a href={"/home"}>
            <h1>Code-Sharing Platform</h1>
          </a>
        </div>

        {isLoggedIn ? (
          <div className={"AccountMenu"}>
            <AccountMenu
              username={username}
              role={role}
              deleteAll={deleteAll}
              logout={logout}
            />
          </div>
        ) : (
          // Another invisible div to balance the layout when AccountMenu is not present
          <div className="Placeholder"></div>
        )}
      </header>
      <hr />
    </>
  );
};

export default AppHeader;
