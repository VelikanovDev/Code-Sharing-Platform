import React from "react";
import { useNavigate } from "react-router-dom";
import MyButton from "./UI/button/MyButton";

const UserActionButtons = ({ logout }) => {
  const navigate = useNavigate();
  return (
    <div className={"actions"}>
      <MyButton
        value={"Add a new snippet"}
        onClick={() => {
          navigate("/new");
        }}
      />
      <MyButton
        value={"Log out"}
        onClick={() => {
          logout();
          navigate("/login");
        }}
      />
    </div>
  );
};

export default UserActionButtons;
