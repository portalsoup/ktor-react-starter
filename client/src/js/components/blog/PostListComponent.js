import React from "react";
import { useSelector } from "react-redux"
import { CreatePostComponent } from "./post/CreatePostComponent";
import { PostCard } from "./post/PostCard"

import css from "./PostListContainer.css";


export const PostListComponent = () => {
    const posts = useSelector(state => state.posts)
    const isLoggedIn = useSelector(state => !!state.currentUser?.id)
    console.log(`Is logged in? ${JSON.stringify(isLoggedIn)}`)


    let postCounter = 1;
    const postList = posts.map(post => {
        return <div
            className={css.postContainer}
            key={postCounter++}
        >
            <PostCard
                post={post}
            />
        </div>
    });

    return (  
        <div>
            {isLoggedIn && <CreatePostComponent></CreatePostComponent>}
            {postList}
        </div>
    )
}