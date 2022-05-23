import { Component, createRef } from "react";
import { Container, Row } from "react-bootstrap";
import Post from "./Post";

class Posts extends Component {

    constructor(props) {
        super(props);
        this.state = {pageNum: 1, hasNextPage: true, isLoading: false, posts: []};
        this.lastPostRef = createRef();
        this.includedKeywordNames = [];
        this.includedColors = {};
        this.props.includedKeywords.forEach(keyword => {
            this.includedKeywordNames.push(keyword.name);
            this.includedColors[keyword.name] = keyword.color;
        });
        this.excludedKeywordNames = [];
        this.excludedColors = {}
        this.props.excludedKeywords.forEach(keyword => {
            this.excludedKeywordNames.push(keyword.name);
            this.excludedColors[keyword.name] = keyword.color;
        });

        this.handleObserver = async (entries) => {
            const target = entries[0];
            if (target.isIntersecting && !this.state.isLoading && this.state.hasNextPage) {
                const response = await fetch(`https://hy-notice.kro.kr/posts?includedKeywords=${this.includedKeywordNames.join(',')}&excludedKeywords=${this.excludedKeywordNames.join(',')}&page=${this.state.pageNum}`);
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
        if (this.props.includedKeywords.length !== nextProps.includedKeywords.length || this.props.excludedKeywords.length !== nextProps.excludedKeywords.length) {
            this.includedKeywordNames = [];
            this.includedColors = {};
            nextProps.includedKeywords.forEach(keyword => {
                this.includedKeywordNames.push(keyword.name);
                this.includedColors[keyword.name] = keyword.color;
            });
            this.excludedKeywordNames = [];
            this.excludedColors = {}
            nextProps.excludedKeywords.forEach(keyword => {
                this.excludedKeywordNames.push(keyword.name);
                this.excludedColors[keyword.name] = keyword.color;
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
            posts.push(<Post isRead={this.props.readIds.has(post.id)} readId={this.props.readId} title={post.title} id={post.id} board={post.board} link={post.url} keywords={post.containingKeywords} colors={this.includedColors}></Post>);
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