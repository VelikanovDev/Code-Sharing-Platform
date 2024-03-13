import React from "react";
import { useNavigate } from "react-router-dom";
import MyButton from "./UI/button/MyButton";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { IconButton } from "@mui/material";

const AdminActionButtons = ({ deleteAll, logout }) => {
  const navigate = useNavigate();

  return (
    <div className={"actions"}>
      <>
        <IconButton
          aria-label="add"
          onClick={() => {
            navigate("/new");
          }}
        >
          <AddCircleIcon color={"primary"} sx={{ fontSize: 16 }} />
        </IconButton>
      </>
      {/*<MyButton*/}
      {/*  value={"Add a new snippet"}*/}
      {/*  onClick={() => {*/}
      {/*    navigate("/new");*/}
      {/*  }}*/}
      {/*/>*/}
      <MyButton
        value={"Show users"}
        onClick={() => alert("Not implemented yet")}
      />
      <MyButton value={"Delete all snippets"} onClick={deleteAll} />
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

export default AdminActionButtons;
