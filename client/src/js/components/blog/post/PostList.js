import React, { Component } from "react";
import { connect } from "react-redux";
import { getPosts } from "../../../actions/BlogActions"
import PostCard from "./PostCard"

import css from './PostList.css';

class PostList extends Component {
    constructor() {
        super();
    }

    componentDidMount() {
        this.props.getPosts();
    }

    render() {
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
        return (
            <div>
                {postList}
            </div>
        );
    }
}

function mapDispatchToProps(dispatch) {
    return {
        getPosts: () => dispatch(getPosts())
    };
}

const mapStateToProps = state => {
    return {
        posts: state.posts
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(PostList);