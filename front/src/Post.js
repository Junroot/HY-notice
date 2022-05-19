import { Component } from "react";
import { Badge, Card } from "react-bootstrap";

class Post extends Component {
    render() {
        const keywords = []

        for(const keyword in this.props.keywords) {
            keywords.push(
                <span className="mx-1 badge" style={{backgroundColor:keyword["color"]}}>keyword["name"]</span>
            );
        }

        return (
            <Card className="my-2">
                <Card.Body>
                    <Card.Title>{this.props.title}</Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">{this.props.board}</Card.Subtitle>
                    <span className="mx-1 badge" style={{backgroundColor:"#dc3545"}}>경제</span>
                    <Badge className="mx-1">장학금</Badge>
                </Card.Body>
            </Card>
        );
    }
}

export default Post;
