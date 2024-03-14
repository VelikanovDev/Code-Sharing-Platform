import React from "react";
import AccountMenu from "./AccountMenu";

const AppHeader = ({ isLoggedIn, deleteAll, logout }) => {
  return (
    <header className="AppHeader">
      {isLoggedIn && (
        <div className={"AccountMenu"}>
          <AccountMenu
            username={localStorage.getItem("username")}
            deleteAll={deleteAll}
            logout={logout}
          />
        </div>
      )}
      <a href={"/home"}>
        <h1>Code-Sharing Platform</h1>
      </a>
      <hr />
    </header>
  );
};

export default AppHeader;
