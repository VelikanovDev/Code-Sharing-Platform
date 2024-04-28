import React, { useEffect, useState } from "react";
import { fetchUsers } from "../services/SnippetService";

const UsersPage = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetchUsers()
      .then((res) => {
        setUsers(res);
      })
      .catch((err) => {
        console.error("Failed to fetch users:", err);
      });
  }, []);

  return (
    <div>
      <h2>Users</h2>
      <div>
        <table id={"users"}>
          <thead>
            <tr>
              <th>Username</th>
              <th>Role</th>
              <th>Snippet posted</th>
              <th>Comments</th>
              <th>Rating provided</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.username}</td>
                <td>{user.role}</td>
                <td>{user.snippets.length}</td>
                <td>{user.comments.length}</td>
                <td>{user.ratings.length}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default UsersPage;
