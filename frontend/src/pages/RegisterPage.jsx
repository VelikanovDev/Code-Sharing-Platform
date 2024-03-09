import React, {useState} from 'react';
import MyInput from "../components/UI/input/MyInput";
import MyButton from "../components/UI/button/MyButton";
import {registerUser} from "../services/SnippetService";
import {useNavigate} from "react-router-dom";

const RegisterPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        if (password !== confirmPassword) {
            setError("Passwords do not match!");
            return;
        }

        if(username === "" || password === "") {
            setError("Username and password are required");
            return;
        }

        try {
            await registerUser({ username: username, password: password });
            alert("Registration successful!");
            navigate("/login");
        } catch (error) {
            alert("Registration failed!");
        }
    };

    return (
        <div>
            <h2>Register a new account</h2>
            {error && <h3 style={ {color: "red"}}>{error}</h3>}
            <form onSubmit={handleSubmit}>
                <MyInput
                    type="text"
                    placeholder="Login"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <MyInput
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <MyInput
                    type="password"
                    placeholder="Confirm password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
                <MyButton type="submit" value={"Register"}/>
            </form>
            <MyButton value={"Back to login"} onClick={() => {navigate("/login")}}/>
        </div>
    );
};

export default RegisterPage;