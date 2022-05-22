import { Component } from "react";
import { Badge, Card } from "react-bootstrap";
import './Post.css';


class Post extends Component {
    render() {
        const keywords = [];

        this.props.keywords.forEach(keyword => {
            keywords.push(
                <span className="mx-1 badge" style={{backgroundColor:this.props.colors[keyword]}}>{keyword}</span>
                // <span className="mx-1 badge" style={{backgroundColor:keyword["color"]}}>keyword["name"]</span>
            );
        });

        let clazz = "";

        if (this.props.isRead) {
            clazz += " read"
        }

        return (
            <Card className={"my-2" + clazz} id={this.props.id}>
                <Card.Body>
                    <a className="card-a"  href={this.props.link} onClick={() => {this.props.readId(this.props.id);}} target='_blank'>
                        <Card.Title>{this.props.title}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">{this.props.board}</Card.Subtitle>
                        {keywords}
                    </a>
                </Card.Body>
            </Card>
        );
    }
}

export default Post;
