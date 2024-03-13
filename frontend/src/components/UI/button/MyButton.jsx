import React from "react";
import classes from "./MyButton.module.css";

const MyButton = (props) => {
  return (
    <button className={classes.myButton} {...props}>
      {props.value}
    </button>
  );
};
export default MyButton;
