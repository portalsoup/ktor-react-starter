import React, { Component } from "react";
import { connect } from "react-redux";
import PostList from "./post/PostList";

class BlogContainer extends Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div>
                <PostList />
            </div>
        )
    }
}

function mapDispatchToProps(dispatch) {
    return {};
}

export default connect(null, mapDispatchToProps)(BlogContainer);