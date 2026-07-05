import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { Container, Alert, Breadcrumb, Spinner } from 'react-bootstrap'
import { getOrchidById } from '../utils/orchidApi'
import { useOrchid } from '../context/OrchidContext'
import OrchidForm from '../components/OrchidForm'

export default function EditOrchidPage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const { editOrchid } = useOrchid()
  const [orchid, setOrchid] = useState(null)
  const [fetching, setFetching] = useState(true)
  const [saving, setSaving] = useState(false)
  const [success, setSuccess] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    getOrchidById(id).then(res => setOrchid(res.data))
      .catch(() => setError('Orchid not found: ' + id))
      .finally(() => setFetching(false))
  }, [id])

  const handleSubmit = async (data) => {
    setSaving(true); setError('')
    try { await editOrchid(Number(id), data); setSuccess(true); setTimeout(() => navigate('/'), 1500) }
    catch (err) { setError(err.response?.data?.message ?? 'Update failed.') }
    finally { setSaving(false) }
  }

  return (
    <Container className="py-4" style={{ maxWidth:720 }}>
      <Breadcrumb>
        <Breadcrumb.Item href="/">Home</Breadcrumb.Item>
        <Breadcrumb.Item active>Edit #{id}</Breadcrumb.Item>
      </Breadcrumb>
      <h3 className="mb-4">Edit orchid #{id}</h3>
      {fetching && <div className="text-center"><Spinner animation="border" /></div>}
      {!fetching && error && <Alert variant="danger">{error}</Alert>}
      {success && <Alert variant="success">Updated successfully! Redirecting...</Alert>}
      {!fetching && orchid && (
        <OrchidForm initialData={orchid} onSubmit={handleSubmit} submitLabel="Update" loading={saving} />
      )}
    </Container>
  )
}
