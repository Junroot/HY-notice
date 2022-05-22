import { Component } from "react";
import { Badge, Button, Container } from "react-bootstrap";
import './Keyword.css';
import KeywordDeleteModal from "./KeywordDeleteModal";

class Keyword extends Component {

    constructor(props) {
        super(props);

        this.state = {isHover: false, keywordDeleteModalShow: false};
    }

    handleHover = () => {
        this.setState({isHover: true});
    }

    handleUnhover = () => {
        this.setState({isHover: false});
    }

    onClickDeletion(event) {
        event.preventDefault();
        this.setState({keywordDeleteModalShow: true});
    }

    delete() {
        this.props.onDelete(this.props.id);
        this.setState({keywordDeleteModalShow: false});
    }

    close() {
        this.setState({keywordDeleteModalShow: false});
    }

    render() {
        let badgeClass = "keyword-delete";
        if (this.state.isHover) {
            badgeClass += " visible";
        }

        return (
            <div className="my-1 keyword-wrapper" id = {"keyword" + this.props.id}>
                <KeywordDeleteModal show={this.state.keywordDeleteModalShow} name={this.props.text} delete={this.delete.bind(this)} close={this.close.bind(this)}></KeywordDeleteModal>
                <Badge pill className="keyword-delete" bg="danger" onClick={this.onClickDeletion.bind(this)}>x</Badge>
                <Button className="keyword-content" onClick={this.onClickDeletion.bind(this)} style={{backgroundColor:this.props.color, border:0}}>{this.props.text}</Button>
            </div>
        );
    }
}

export default Keyword;