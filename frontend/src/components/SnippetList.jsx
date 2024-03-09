import Snippet from "./Snippet";

const SnippetList = ({username, role, snippets, editSnippet, deleteSnippet}) => {
    return (
        <div className={"snippetList"}>
            {snippets.map((s) => {
                if(s.user.username === username) {
                    return <Snippet key={s.id} snippet={s} editSnippet={editSnippet} deleteSnippet={deleteSnippet}></Snippet>
                }
                else {
                    return <Snippet key={s.id} snippet={s}></Snippet>
                }
            })}
        </div>
    )
}

export default SnippetList;