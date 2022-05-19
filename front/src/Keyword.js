import { Component } from "react";
import { Badge, Button, Container } from "react-bootstrap";
import './Keyword.css';

class Keywords extends Component {

    constructor(props) {
        super(props);

        this.state = {isHover: false};
    }

    handleHover = () => {
        this.setState({isHover: true});
    }

    handleUnhover = () => {
        this.setState({isHover: false});
    }

    render() {
        let badgeClass = "keyword-delete";
        if (this.state.isHover) {
            badgeClass += " visible";
        }

        return (
            <div className="my-1 keyword-wrapper">
                <Badge pill onMouseOver={this.handleHover} onMouseLeave={this.handleUnhover} className={badgeClass} bg="danger">x</Badge>
                <Button onMouseOver={this.handleHover} onMouseLeave={this.handleUnhover} className="keyword-content" variant="primary">{this.props.text}</Button>
            </div>
        );
    }
}

export default Keywords;