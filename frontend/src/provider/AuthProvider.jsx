import axios from "axios";
import {
  createContext,
  useContext,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [userDetails, setUserDetails] = useState(null);

  const userDetailsRef = useRef({
    username: "",
    role: "",
  });

  const decodeToken = (token) => {
    if (token) {
      const decoded = jwtDecode(token);
      const newDetails = {
        username: decoded.username,
        role: decoded.role,
      };
      userDetailsRef.current = newDetails;
      setUserDetails(newDetails);
    } else {
      userDetailsRef.current = { username: "", role: "" };
      setUserDetails(null);
      delete axios.defaults.headers.common["Authorization"];
      localStorage.removeItem("token");
    }
  };

  useEffect(() => {
    decodeToken(token);
  }, [token]);

  // Memoized value of the authentication context
  const contextValue = useMemo(
    () => ({
      userDetails: userDetailsRef.current,
      setUserDetails,
      token,
      setToken,
    }),
    [userDetails, token],
  );

  // Provide the authentication context to the children components
  return (
    <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};
