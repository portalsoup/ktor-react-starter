import React from "react";
import { useDispatch, useSelector } from "react-redux"

import css from './PostList.css';


export const PostListComponent = (props) => {
    const posts = useSelector(state => state.posts)


    let postCounter = 1;
    const postList = this.props.posts.map(post => {
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