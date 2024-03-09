import './styles/App.css';
import {useEffect, useState} from "react";
import {addNewSnippet, deleteAllSnippets, deleteSnippet, editSnippet, fetchSnippets} from "./services/SnippetService";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import {createBrowserRouter, Navigate, Outlet, RouterProvider} from "react-router-dom";
import HomePage from "./pages/HomePage";
import NewSnippetPage from "./pages/NewSnippetPage";
import EditPage from "./pages/EditPage";

function App() {
    const [snippets, setSnippets] = useState([]);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [refreshSnippets, setRefreshSnippets] = useState(false);

    useEffect(() => {
        const loggedIn = localStorage.getItem('isLoggedIn') === 'true';
        setIsLoggedIn(loggedIn);

        if (loggedIn) {
            fetchSnippets()
                .then((res) => {
                    setSnippets(res);
                    setRefreshSnippets(false);
                })
                .catch((err) => {
                    console.error("Failed to fetch snippets:", err)
                    if(err.response && err.response.status === 401) {
                        handleLogout();
                    }
                });
        }
    }, [refreshSnippets]);

    const handleLoginSuccess = () => {
        setIsLoggedIn(true);
        setRefreshSnippets(true);
        localStorage.setItem('isLoggedIn', 'true');
    };

    const handleAddNewSnippet = async (snippetText) => {
        try {
            const result = await addNewSnippet(localStorage.getItem("username"), snippetText);
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
    }

    const handleDeleteAll = async () => {
        try {
            const result = await deleteAllSnippets();
            setRefreshSnippets(true);
            console.log(result);
        } catch (error) {
            console.error("Error in handleAddNewSnippet", error);
            throw error; // Rethrow if you want calling context to handle it
        }
    }

    const handleLogout = () => {
        localStorage.removeItem("isLoggedIn");
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        setIsLoggedIn(false);
    }

    const handleEditSnippet = async (snippet) => {
        try {
            const result = await editSnippet(snippet);
            setRefreshSnippets(true);
            console.log(result);
        } catch (error) {
            console.error("Error in handleEditSnippet", error);
            throw error; // Rethrow if you want calling context to handle it
        }
    }

    // Define a wrapper or layout component that checks for isLoggedIn state
    const ProtectedRoute = ({ children }) => {
        const loggedIn = localStorage.getItem('isLoggedIn') === 'true';
        return loggedIn ? children : <Navigate to="/login" replace />;
    };

    const router = createBrowserRouter([
        {
            path: "/",
            element: <Outlet />, // Serves as a placeholder for nested routes
            children: [
                {
                    path: "/",
                    element: isLoggedIn ? <Navigate to="/home" replace /> : <Navigate to="/login" replace />
                },
                { path: "login", element: <LoginPage onLoginSuccess={handleLoginSuccess} /> },
                { path: "register", element: <RegisterPage /> },
                {
                    path: "home",
                    element: (
                        <ProtectedRoute>
                            <HomePage
                                username={localStorage.getItem("username")}
                                role={localStorage.getItem("role")}
                                snippetList={snippets}
                                editSnippet={handleEditSnippet}
                                deleteSnippet={handleDeleteSnippet}
                                deleteAll={handleDeleteAll}
                                logout={handleLogout}/>
                        </ProtectedRoute>
                    ),
                },
                {
                    path: "edit",
                    element: (
                        <ProtectedRoute>
                            <EditPage editSnippet={handleEditSnippet}/>
                        </ProtectedRoute>
                    )
                },
                {
                    path: "new",
                    element: (
                        <ProtectedRoute>
                            <NewSnippetPage addNewSnippet={handleAddNewSnippet}/>
                        </ProtectedRoute>
                    ),
                }
            ],
        },
    ]);

    return (
      <div className="App">
          <h1>Code-Sharing Platform</h1>
          <RouterProvider router={router}/>
      </div>
    );
}

export default App;
