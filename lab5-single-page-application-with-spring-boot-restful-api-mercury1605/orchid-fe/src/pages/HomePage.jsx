import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Alert,
  Badge,
  Button,
  ButtonGroup,
  Card,
  Col,
  Row,
  Spinner,
} from 'react-bootstrap';
import { useOrchid } from '../context/OrchidContext';
import OrchidTable from '../components/OrchidTable';
import ConfirmModal from '../components/ConfirmModal';

export default function HomePage() {
  const { orchids, loading, error, fetchOrchids, removeOrchid } = useOrchid();
  const navigate = useNavigate();

  const [deleteTarget, setDeleteTarget] = useState(null);
  const [deleteLoading, setDeleteLoading] = useState(false);
  const [viewMode, setViewMode] = useState('table');

  useEffect(() => {
    fetchOrchids();
  }, [fetchOrchids]);

  const handleEdit = (id) => navigate(`/edit/${id}`);

  const handleDeleteClick = (id) => {
    const orchid = orchids.find((o) => o.orchidId === id);
    setDeleteTarget(orchid);
  };

  const handleConfirmDelete = async () => {
    if (!deleteTarget) return;
    setDeleteLoading(true);
    try {
      await removeOrchid(deleteTarget.orchidId);
    } finally {
      setDeleteLoading(false);
      setDeleteTarget(null);
    }
  };

  return (
    <>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2 className="mb-0">Orchid List</h2>
        <div className="d-flex gap-2">
          <ButtonGroup>
            <Button
              variant={viewMode === 'table' ? 'dark' : 'outline-dark'}
              onClick={() => setViewMode('table')}
            >
              Table
            </Button>
            <Button
              variant={viewMode === 'card' ? 'dark' : 'outline-dark'}
              onClick={() => setViewMode('card')}
            >
              Card
            </Button>
          </ButtonGroup>
          <Button variant="success" onClick={() => navigate('/add')}>
            + Add Orchid
          </Button>
        </div>
      </div>

      {loading && (
        <div className="text-center py-5">
          <Spinner animation="border" role="status">
            <span className="visually-hidden">Loading...</span>
          </Spinner>
        </div>
      )}

      {error && <Alert variant="danger">{error}</Alert>}

      {!loading && !error && viewMode === 'table' && (
        <OrchidTable
          orchids={orchids}
          onEdit={handleEdit}
          onDelete={handleDeleteClick}
        />
      )}

      {!loading && !error && viewMode === 'card' && (
        <Row xs={1} sm={2} md={3} lg={4} className="g-3">
          {orchids.length === 0 ? (
            <Col>
              <p className="text-muted">No orchids found</p>
            </Col>
          ) : (
            orchids.map((orchid) => (
              <Col key={orchid.orchidId}>
                <Card className="h-100 shadow-sm">
                  {orchid.orchidURL && (
                    <Card.Img
                      variant="top"
                      src={orchid.orchidURL}
                      style={{ height: 180, objectFit: 'cover' }}
                      onError={(e) => {
                        e.target.style.display = 'none';
                      }}
                    />
                  )}
                  <Card.Body className="d-flex flex-column">
                    <Card.Title>{orchid.orchidName}</Card.Title>
                    <Card.Text className="text-muted small flex-grow-1">
                      {orchid.orchidDescription}
                    </Card.Text>
                    <div className="d-flex gap-1 mb-2 flex-wrap">
                      <Badge bg="info" text="dark">
                        {orchid.orchidCategory}
                      </Badge>
                      {orchid.isNatural && (
                        <Badge bg="success">Natural</Badge>
                      )}
                      {orchid.isAttractive && (
                        <Badge bg="warning" text="dark">
                          Attractive
                        </Badge>
                      )}
                    </div>
                    <div className="d-flex gap-2 mt-auto">
                      <Button
                        variant="outline-primary"
                        size="sm"
                        className="flex-fill"
                        onClick={() => handleEdit(orchid.orchidId)}
                      >
                        Edit
                      </Button>
                      <Button
                        variant="outline-danger"
                        size="sm"
                        className="flex-fill"
                        onClick={() => handleDeleteClick(orchid.orchidId)}
                      >
                        Delete
                      </Button>
                    </div>
                  </Card.Body>
                </Card>
              </Col>
            ))
          )}
        </Row>
      )}

      <ConfirmModal
        show={!!deleteTarget}
        onHide={() => setDeleteTarget(null)}
        onConfirm={handleConfirmDelete}
        orchidName={deleteTarget?.orchidName}
        loading={deleteLoading}
      />
    </>
  );
}
