import { Modal , Button, Form } from "react-bootstrap";
import { Component } from "react";

class KeywordDeleteModal extends Component {

    onClickSubmit(event) {
        event.preventDefault();
        this.props.delete();
    }

    onClickCancel(event) {
        event.preventDefault();
        this.props.close(event);
    }

    render() {
        return (
            <Modal show={this.props.show}>
                <Modal.Header closeButton>
                    <Modal.Title>'{this.props.name}' 키워드를 삭제하시겠습니까?</Modal.Title>
                </Modal.Header>
                <Modal.Footer>
                    <Button variant="primary" onClick={this.onClickSubmit.bind(this)}>
                        네
                    </Button>
                    <Button variant="secondary" onClick={this.onClickCancel.bind(this)}>
                        아니오
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default KeywordDeleteModal;
