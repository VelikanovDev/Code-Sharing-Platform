import MyInput from "../components/UI/input/MyInput";
import { useState } from "react";
import MyButton from "../components/UI/button/MyButton";
import { login } from "../services/SnippetService";
import { useNavigate } from "react-router-dom";
const LoginPage = ({ onLoginSuccess }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    if (username === "" || password === "") {
      setError("Username and password are required");
      return;
    }

    const success = await login({ username: username, password: password });

    if (success) {
      onLoginSuccess(success);
      navigate("/home");
    } else {
      setError("Incorrect username or password");
    }
  };

  return (
    <div>
      <h2>Login</h2>
      {error && <h3 style={{ color: "red", textAlign: "center" }}>{error}</h3>}
      <form onSubmit={handleLogin}>
        <MyInput
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <MyInput
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <MyButton type="submit" value={"Sign In"} />
      </form>
      <MyButton
        value={"Create a new account"}
        onClick={() => {
          navigate("/register");
        }}
      />
    </div>
  );
};

export default LoginPage;
