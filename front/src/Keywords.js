import { Component } from "react";
import { Container, Button, ToggleButton, Row } from "react-bootstrap";
import Keyword from "./Keyword"

class Keywords extends Component {
    render() {
        return  (
            <Container className="col-md-10 my-4" fluid>
                <Row>
                    <h2>키워드</h2>
                    <Container fluid>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                        <Keyword text = "12312"></Keyword>
                    </Container>
                </Row>
            </Container>
        );
    }
}

export default Keywords;