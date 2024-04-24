import axios from "axios";

const API_BASE_URL = "http://localhost:8080";
export const fetchSnippets = async () => {
  const token = localStorage.getItem("token"); // Get the token from local storage

  try {
    const response = await axios.get(`${API_BASE_URL}/snippet/latest`, {
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
      return token;
    } else {
      console.error("Login failed:", response.data);
      return null;
    }
  } catch (error) {
    console.error(
      `Error during login: ${error.response ? error.response.data : error}`,
    );
    return false;
  }
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

export const addNewSnippet = async (code) => {
  const token = localStorage.getItem("token");
  try {
    const response = await axios.post(
      `${API_BASE_URL}/snippet/new`,
      {
        code,
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

export const fetchUsers = async () => {
  const token = localStorage.getItem("token");

  try {
    const response = await axios.get(`${API_BASE_URL}/users`, {
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

export const deleteSnippet = async (snippetId) => {
  const token = localStorage.getItem("token");
  try {
    const response = await axios.delete(
      `${API_BASE_URL}/snippet/delete/${snippetId}`,
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
    const response = await axios.delete(`${API_BASE_URL}/snippet/delete`, {
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
      `${API_BASE_URL}/snippet/update/${snippet.id}`,
      snippet,
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

export const addComment = async (username, snippetId, text) => {
  const token = localStorage.getItem("token");
  try {
    const response = await axios.post(
      `${API_BASE_URL}/comment/add/${snippetId}`,
      {
        username,
        text,
      },
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

export const deleteComment = async (commentId) => {
  const token = localStorage.getItem("token");
  try {
    const response = await axios.delete(
      `${API_BASE_URL}/comment/delete/${commentId}`,
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
