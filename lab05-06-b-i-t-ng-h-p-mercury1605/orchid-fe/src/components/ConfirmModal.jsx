import { Modal, Button } from 'react-bootstrap'

export default function ConfirmModal({ show, onHide, onConfirm, orchidName, loading }) {
  return (
    <Modal show={show} onHide={onHide} centered>
      <Modal.Header closeButton><Modal.Title>Confirm deletion</Modal.Title></Modal.Header>
      <Modal.Body>Are you sure you want to delete <strong className="text-danger">{orchidName}</strong>?</Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide} disabled={loading}>Cancel</Button>
        <Button variant="danger" onClick={onConfirm} disabled={loading}>
          {loading ? 'Deleting...' : 'Delete'}
        </Button>
      </Modal.Footer>
    </Modal>
  )
}
