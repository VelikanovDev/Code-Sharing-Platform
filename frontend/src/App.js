import "./styles/App.css";
import React, { useEffect, useState } from "react";
import {
  addComment,
  addNewSnippet,
  deleteAllSnippets,
  deleteComment,
  deleteSnippet,
  editSnippet,
  fetchSnippets,
  getUsernameAndRoleFromToken,
} from "./services/SnippetService";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import {
  createBrowserRouter,
  Navigate,
  RouterProvider,
} from "react-router-dom";
import HomePage from "./pages/HomePage";
import NewSnippetPage from "./pages/NewSnippetPage";
import EditPage from "./pages/EditPage";
import UsersPage from "./pages/UsersPage";
import Layout from "./components/Layout";

function App() {
  const [snippets, setSnippets] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [refreshSnippets, setRefreshSnippets] = useState(false);
  const [userData, setUserData] = useState({ username: null, role: null });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const loggedIn = localStorage.getItem("isLoggedIn") === "true";
    setIsLoggedIn(loggedIn);

    if (loggedIn) {
      if (userData.username === null) {
        const data = getUsernameAndRoleFromToken();

        if (data) {
          setUserData({ username: data.username, role: data.role });
        }
      }

      setIsLoading(true);
      fetchSnippets()
        .then((res) => {
          setSnippets(res);
          setRefreshSnippets(false);
        })
        .catch((err) => {
          console.error("Failed to fetch snippets:", err);
          if (err.response && err.response.status === 401) {
            handleLogout();
          }
        })
        .finally(() => setIsLoading(false));
    }
  }, [refreshSnippets, userData.username]);

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
    setRefreshSnippets(true);
    localStorage.setItem("isLoggedIn", "true");
  };

  const handleAddNewSnippet = async (snippetText) => {
    try {
      const result = await addNewSnippet(userData.username, snippetText);
      setRefreshSnippets(true);
      console.log(result);
    } catch (error) {
      console.error("Error in handleAddNewSnippet", error);
      throw error;
    }
  };

  const handleDeleteSnippet = async (snippetId) => {
    try {
      const result = await deleteSnippet(snippetId);
      setRefreshSnippets(true);
      console.log(result);
    } catch (error) {
      console.error("Error in handleDeleteSnippet", error);
      throw error;
    }
  };

  const handleDeleteAll = async () => {
    if (window.confirm("Do you really want to delete all snippets?")) {
      try {
        const result = await deleteAllSnippets();
        setRefreshSnippets(true);
        console.log(result);
      } catch (error) {
        console.error("Error in handleAddNewSnippet", error);
        throw error; // Rethrow if you want calling context to handle it
      }
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    setIsLoggedIn(false);
    setUserData({ username: null, role: null });
  };

  const handleEditSnippet = async (snippet) => {
    try {
      const result = await editSnippet(snippet);
      setRefreshSnippets(true);
      console.log(result);
    } catch (error) {
      console.error("Error in handleEditSnippet", error);
      throw error; // Rethrow if you want calling context to handle it
    }
  };

  const handleAddComment = async (snippetId, commentText) => {
    try {
      const newComment = await addComment(
        userData.username,
        snippetId,
        commentText,
      );
      return newComment;
    } catch (error) {
      console.error("Error in handleAddComment", error);
      throw error;
    }
  };

  const handleDeleteComment = async (commentId) => {
    try {
      const result = deleteComment(commentId);
      console.log(result);
    } catch (error) {
      console.error("Error in handleDeleteComment", error);
      throw error;
    }
  };

  const ProtectedRoute = ({ children }) => {
    const loggedIn = localStorage.getItem("isLoggedIn") === "true";
    return loggedIn ? children : <Navigate to="/login" replace />;
  };

  const RoleProtectedRoute = ({ children }) => {
    const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
    const userIsAdmin = userData.role === "ADMIN";
    if (!isLoggedIn) {
      return <Navigate to="/login" replace />;
    } else if (!userIsAdmin) {
      return <Navigate to="/home" replace />;
    } else {
      return children;
    }
  };

  const router = createBrowserRouter([
    {
      path: "/",
      element: (
        <Layout
          userdata={userData}
          isLoggedIn={isLoggedIn}
          handleLogout={handleLogout}
          handleDeleteAll={handleDeleteAll}
        />
      ),
      children: [
        {
          path: "/",
          element: isLoggedIn ? (
            <Navigate to="/home" replace />
          ) : (
            <Navigate to="/login" replace />
          ),
        },
        {
          path: "login",
          element: isLoggedIn ? (
            <Navigate to="/home" replace />
          ) : (
            <LoginPage onLoginSuccess={handleLoginSuccess} />
          ),
        },
        { path: "register", element: <RegisterPage /> },
        {
          path: "home",
          element: (
            <ProtectedRoute>
              <HomePage
                username={userData.username}
                role={userData.role}
                snippetList={snippets}
                deleteSnippet={handleDeleteSnippet}
                addComment={handleAddComment}
                deleteComment={handleDeleteComment}
                isLoading={isLoading}
              />
            </ProtectedRoute>
          ),
        },
        {
          path: "edit",
          element: (
            <ProtectedRoute>
              <EditPage editSnippet={handleEditSnippet} />
            </ProtectedRoute>
          ),
        },
        {
          path: "new",
          element: (
            <ProtectedRoute>
              <NewSnippetPage addNewSnippet={handleAddNewSnippet} />
            </ProtectedRoute>
          ),
        },
        {
          path: "users",
          element: (
            <RoleProtectedRoute>
              <UsersPage />
            </RoleProtectedRoute>
          ),
        },
      ],
    },
  ]);

  return (
    <div className="App">
      <RouterProvider router={router} />
    </div>
  );
}

export default App;
