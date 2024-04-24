import React from "react";
import AccountMenu from "./AccountMenu";
import { useAuth } from "../provider/AuthProvider";

const AppHeader = ({ deleteAll, logout }) => {
  const { token } = useAuth();

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

        {token ? (
          <div className={"AccountMenu"}>
            <AccountMenu deleteAll={deleteAll} logout={logout} />
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
