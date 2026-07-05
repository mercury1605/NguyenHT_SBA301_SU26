import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Container, Button, ButtonGroup, Spinner, Alert } from 'react-bootstrap'
import { useOrchid } from '../context/OrchidContext'
import { useAuth } from '../context/AuthContext'
import OrchidTable from '../components/OrchidTable'
import ConfirmModal from '../components/ConfirmModal'

export default function HomePage() {
  const navigate = useNavigate()
  const { orchids, loading, error, fetchOrchids, removeOrchid } = useOrchid()
  const { token } = useAuth()
  const [viewMode, setViewMode] = useState('table')
  const [deleteTarget, setDeleteTarget] = useState(null)
  const [deleting, setDeleting] = useState(false)

  useEffect(() => { fetchOrchids() }, [fetchOrchids])

  const handleDelete = async () => {
    setDeleting(true)
    try { await removeOrchid(deleteTarget.orchidId); setDeleteTarget(null) }
    catch (err) { alert('Delete failed: ' + (err.response?.data?.message ?? err.message)) }
    finally { setDeleting(false) }
  }

  return (
    <Container className="py-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Orchid List</h2>
        <div className="d-flex gap-2">
          <ButtonGroup>
            <Button variant={viewMode==='table'?'dark':'outline-dark'} onClick={() => setViewMode('table')}>Table</Button>
            <Button variant={viewMode==='card'?'dark':'outline-dark'} onClick={() => setViewMode('card')}>Card</Button>
          </ButtonGroup>
          {token && <Button variant="success" onClick={() => navigate('/add')}>+ Add new</Button>}
        </div>
      </div>
      {loading && <div className="text-center py-5"><Spinner animation="border" variant="primary" /></div>}
      {error && <Alert variant="danger">{error}</Alert>}
      {!loading && !error && (
        <OrchidTable orchids={orchids} onEdit={id => navigate(`/edit/${id}`)} onDelete={setDeleteTarget} />
      )}
      <ConfirmModal show={!!deleteTarget} onHide={() => setDeleteTarget(null)}
        onConfirm={handleDelete} orchidName={deleteTarget?.orchidName} loading={deleting} />
    </Container>
  )
}
