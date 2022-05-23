import { Component } from "react";
import { Container, Row, Button, Col } from "react-bootstrap";
import Keyword from "./Keyword"
import KeywordCreateModal from "./KeywordCreateModal";

class Keywords extends Component {

    constructor(props) {
        super(props);
        this.state = {keywordModalShow: false}
    }

    showModal(event) {
        event.preventDefault();
        this.setState({keywordModalShow: true});
    }

    closeModal(event) {
        event.preventDefault();
        this.setState({keywordModalShow: false});
    }

    render() {
        return  (
            <Container className="col-md-10 my-4" fluid>
                <Row>
                    <KeywordCreateModal isValidKeyword={this.props.isValidKeyword} show={this.state.keywordModalShow} submit={this.props.addKeyword} close={this.closeModal.bind(this)}></KeywordCreateModal>
                    <Container fluid>
                        <Row>
                            <Col md="10">
                                <h2>{this.props.title}</h2>
                            </Col>
                            <Col md="2">
                                <Button className="w-100" size="sm" onClick={this.showModal.bind(this)}>추가하기</Button>
                            </Col>
                        </Row>
                    </Container>
                    
                    <Container fluid>
                        {this.props.keywords.map((keyword, id) => 
                            (
                                <Keyword text={keyword.name} color={keyword.color} id={id} onDelete={this.props.deleteKeyword}></Keyword>
                            )
                        )}
                    </Container>
                </Row>
            </Container>
        );
    }
}

export default Keywords;