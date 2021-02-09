import React from "react";
import { useDispatch } from "react-redux"
import { onLogIn, onLogOut } from "../../../actions/AuthActions";
import { createPost } from "../../../actions/BlogActions";

export const CreatePostComponent = () => {
    const dispatch = useDispatch()

    const titleRef = React.createRef()
    const bodyRef = React.createRef()

    const handleClick = (e) => {
        e.preventDefault();
        const title = titleRef.current.value;
        const body = bodyRef.current.value;
        dispatch(createPost(title, body))
    }

    return (
        <div>
            <form id="createPostForm" onSubmit={handleClick.bind(this)}>
                <h3>Create new post</h3>
                Title: <input type="text" ref={titleRef} placeholder="Title" />
                <input type="submit" value="Save" />
            </form>
            <textarea name="body" ref={bodyRef} form="createPostForm" defaultValue="Post body goes here..."></textarea>
        </div>
    )
}