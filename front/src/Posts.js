import { Component, createRef } from "react";
import { Container, Row } from "react-bootstrap";
import Post from "./Post";

class Posts extends Component {

    constructor(props) {
        super(props);
        this.state = {pageNum: 1, hasNextPage: true, isLoading: false, posts: []};
        this.lastPostRef = createRef();
        this.keywordNames = [];
        this.colors = {};
        this.props.keywords.forEach(keyword => {
            this.keywordNames.push(keyword.name);
            this.colors[keyword.name] = keyword.color;
        });

        this.handleObserver = async (entries) => {
            const target = entries[0];
            if (target.isIntersecting && !this.state.isLoading && this.state.hasNextPage) {
                const response = await fetch(`https://hy-notice.kro.kr/posts?keywords=${this.keywordNames.join(',')}&page=${this.state.pageNum}`);
                const newPosts = await response.json();
                this.setState((state) => {return {posts: state.posts.concat(newPosts)}});
                this.setState((state) => {return {pageNum: state.pageNum + 1}});
                if (newPosts.length === 0) {
                    this.setState({hasNextPage: false});
                }
            }
        };
    }

    componentDidMount() {
        this.observer = new IntersectionObserver(this.handleObserver);     
        if (this.lastPostRef.current) {
            this.observer.observe(this.lastPostRef.current);
        }  
    }

    shouldComponentUpdate(nextProps, nextState) {
        if (this.props.keywords.length !== nextProps.keywords.length) {
            this.keywordNames = [];
            this.colors = {};
            nextProps.keywords.forEach(keyword => {
                this.keywordNames.push(keyword.name);
                this.colors[keyword.name] = keyword.color;
            });
            this.setState({pageNum: 1, hasNextPage: true, isLoading: false, posts: []});
        }
        return true;
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        this.observer = new IntersectionObserver(this.handleObserver);     
        if (this.lastPostRef.current) {
            this.observer.observe(this.lastPostRef.current);
        }  
    }

    render() {
        const posts = []
        this.state.posts.forEach(post => {
            posts.push(<Post isRead={this.props.readIds.has(post.id)} readId={this.props.readId} title={post.title} id={post.id} board={post.board} link={post.url} keywords={post.containingKeywords} colors={this.colors}></Post>);
        });

        posts.push(<div ref={this.lastPostRef}></div>);

        return (
            <Container className="col-md-10 my-4" fluid>
                <Row>
                    <h2>게시글</h2>
                    <Container fluid>
                        {posts}
                        
                    </Container>
                </Row>
            </Container>
        );
    }
}

export default Posts;