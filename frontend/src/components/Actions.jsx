import React from 'react';
import MyButton from "./UI/button/MyButton";
import { useNavigate } from 'react-router-dom';


const Actions = ({deleteAll, logout}) => {
    const navigate = useNavigate();

    return (
        <div className={"actions"}>
            <MyButton value={"Add a new snippet"} onClick={() => {
                navigate("/new");
            }}/>
            <MyButton value={"Show users"}/>
            <MyButton value={"Delete all snippets"} onClick={deleteAll}/>
            <MyButton value={"Log out"} onClick={ () => {
                logout();
                navigate("/login");
            }}/>
        </div>
    )
}

export default Actions;