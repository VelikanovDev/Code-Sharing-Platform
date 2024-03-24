import axios from "axios";
import { jwtDecode } from "jwt-decode";

const API_BASE_URL = "http://localhost:8080";
export const fetchSnippets = async () => {
  const token = localStorage.getItem("token"); // Get the token from local storage

  try {
    const response = await axios.get(`${API_BASE_URL}/api/code/latest`, {
      headers: { Authorization: `Bearer ${token}` },
    });

    return response.data;
  } catch (error) {
    console.error("Error fetching snippets:", error);
    throw error;
  }
};

export const login = async (userData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/login`, userData, {
      headers: {
        "Content-Type": "application/json",
      },
    });

    // Check for a successful response (status code 200)
    if (response.status === 200) {
      const { token } = response.data;
      localStorage.setItem("token", token);
      return true;
    } else {
      console.error("Login failed:", response.data);
      return false;
    }
  } catch (error) {
    console.error(
      `Error during login: ${error.response ? error.response.data : error}`,
    );
    return false;
  }
};

export const getUsernameAndRoleFromToken = () => {
  const token = localStorage.getItem("token");
  if (token) {
    return jwtDecode(token);
  }
  return null; // No token or role found
};

export const registerUser = async (userData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/register`, userData);
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Registration failed:", error.response.data);
    throw error; // Rethrow to handle it in the UI component
  }
};

export const addNewSnippet = async (username, snippetText) => {
  const token = localStorage.getItem("token");
  try {
    const response = await axios.post(
      `${API_BASE_URL}/api/code/new`,
      {
        username,
        code: snippetText,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      },
    );
    return `Snippet with id ${response.data.id} was successfully added`;
  } catch (error) {
    throw new Error(
      error.response ? error.response.data.message : error.message,
    );
  }
};

// export const showUsers = async () => {
//   const token = localStorage.getItem("token");
//
//   try {
//     const response = await axios.get(`${API_BASE_URL}/api/code/users`, {
//       headers: {
//         Authorization: `Bearer ${token}`,
//         "Content-Type": "application/json",
//       },
//     });
//     return response.data;
//   } catch (error) {
//     throw new Error(
//       error.response ? error.response.data.message : error.message,
//     );
//   }
// };

export const deleteSnippet = async (snippetId) => {
  const token = localStorage.getItem("token");
  try {
    const response = await axios.delete(
      `${API_BASE_URL}/api/code/delete/${snippetId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      },
    );

    return response.data;
  } catch (error) {
    throw new Error(
      error.response ? error.response.data.message : error.message,
    );
  }
};

export const deleteAllSnippets = async () => {
  const token = localStorage.getItem("token");

  try {
    const response = await axios.delete(`${API_BASE_URL}/api/code/delete`, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    return response.data;
  } catch (error) {
    throw new Error(
      error.response ? error.response.data.message : error.message,
    );
  }
};

export const editSnippet = async (snippet) => {
  const token = localStorage.getItem("token");
  try {
    const response = await axios.post(
      `${API_BASE_URL}/api/code/edit/${snippet.id}`,
      snippet,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      },
    );

    console.log(`In edit snippet: ${response.data}`);
    return response.data;
  } catch (error) {
    throw new Error(
      error.response ? error.response.data.message : error.message,
    );
  }
};
//
// // Setup Axios to automatically include the JWT in every request
// export const setAuthToken = (token) => {
//     if (token) {
//         axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
//     } else {
//         delete axios.defaults.headers.common['Authorization'];
//     }
// };
//
// // You might want to call this function when your app starts to ensure the token is set
// export const loadAuthToken = () => {
//     const token = localStorage.getItem("token");
//     setAuthToken(token);
// };
