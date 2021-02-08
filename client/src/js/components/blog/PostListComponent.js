import React from "react";
import { useSelector } from "react-redux"

import css from './PostList.css';


export const PostListComponent = () => {
    const posts = useSelector(state => state.posts)


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

    return <div>{postList}</div>
}