import React, {useState} from 'react';
import MyButton from "../components/UI/button/MyButton";
import {useNavigate} from 'react-router-dom';

const NewSnippetPage = ( {addNewSnippet} ) => {
    const [snippetText, setSnippetText] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await addNewSnippet(snippetText);
            console.log("Snippet added successfully", snippetText);
            navigate("/home")
        } catch (error) {
            console.error("Failed to add new snippet", error);
        }
    };

    return (
        <div className={"newSnippet"}>
            <h2>Add a new snippet</h2>
            <form onSubmit={handleSubmit}>
                <textarea
                    id="myInput"
                    placeholder="Enter your snippet"
                    value={snippetText}
                    onChange={(e) => setSnippetText(e.target.value)}
                ></textarea>
                <MyButton
                    value={"Submit"}
                    type="submit"
                />
                {/*<MyButton onClick={navigate("/home")} value={"Back to home"}/>*/}
            </form>
        </div>
    );
};

export default NewSnippetPage;