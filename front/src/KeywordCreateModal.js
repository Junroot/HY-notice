import { Modal , Button, Form } from "react-bootstrap";
import { Component } from "react";

class KeywordCreateModal extends Component {

    constructor(props) {
        super(props);
        this.state = {
            value: '',
            isValid: false
        };
    }

    handleChange(event) {
        event.preventDefault();
        this.setState({value: event.target.value});
        this.setState({isValid: this.props.isValidKeyword(event.target.value)})
    }

    handleKeydown(event) {
        if (event.key === 'Enter') {
            this.onClickSubmit(event);
        }
    }

    onClickSubmit(event) {
        event.preventDefault();
        if (this.state.isValid) {
            this.props.submit(this.state.value);
            this.setState({isValid: false})
            this.props.close(event);
        }
    }

    onClickCancel(event) {
        event.preventDefault();
        this.props.close(event);
    }

    render() {
        return (
            <Modal show={this.props.show}>
                <Modal.Header closeButton>
                    <Modal.Title>추가할 키워드를 입력해주세요.</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form.Group controlId="formKeyword" onSubmit={this.onClickSubmit.bind(this)}>
                        <Form.Control
                            type="text"
                            onChange={this.handleChange.bind(this)}
                            onKeyDown={this.handleKeydown.bind(this)}
                            aria-describedby="keywordHelpBlock"
                            isInvalid={!this.state.isValid}
                            autoFocus
                        />
                        <Form.Text id="keywordHelpBlock" muted>
                            키워드는 2~20자의 한글, 영어, 숫자로 구성할 수 있습니다.
                        </Form.Text>
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={this.onClickSubmit.bind(this)} disabled={!this.state.isValid}>
                        추가
                    </Button>
                    <Button variant="secondary" onClick={this.onClickCancel.bind(this)}>
                        취소
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default KeywordCreateModal;