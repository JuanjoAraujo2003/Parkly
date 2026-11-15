import { useEffect, useMemo, useState } from 'react'
import {
  ArrowDownToLine,
  ArrowUpRight,
  CarFront,
  ChevronDown,
  CircleHelp,
  Clock3,
  LayoutDashboard,
  LogOut,
  Menu,
  ParkingSquare,
  Plus,
  Search,
  Settings,
  Ticket,
  TrendingUp,
  WalletCards,
  X,
} from 'lucide-react'

const API_URL = 'http://localhost:8080/api'

const statusLabels = {
  AVAILABLE: 'Disponible',
  RESERVED: 'Reservada',
  OCCUPIED: 'Ocupada',
  MAINTENANCE: 'Mantenimiento',
}

const statusClasses = {
  AVAILABLE: 'space--available',
  RESERVED: 'space--reserved',
  OCCUPIED: 'space--occupied',
  MAINTENANCE: 'space--maintenance',
}

function App() {
  const [dashboard, setDashboard] = useState(null)
  const [tariffs, setTariffs] = useState([])
  const [activeSection, setActiveSection] = useState('Resumen')
  const [isSidebarOpen, setIsSidebarOpen] = useState(false)
  const [isTariffModalOpen, setIsTariffModalOpen] = useState(false)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    Promise.all([
      fetch(`${API_URL}/dashboard`).then((response) => {
        if (!response.ok) throw new Error('No fue posible cargar el dashboard')
        return response.json()
      }),
      fetch(`${API_URL}/tariffs`).then((response) => {
        if (!response.ok) throw new Error('No fue posible cargar las tarifas')
        return response.json()
      }),
    ])
      .then(([dashboardData, tariffsData]) => {
        setDashboard(dashboardData)
        setTariffs(tariffsData)
      })
      .catch(() => setError('No se pudo conectar con la API. Verifica que el backend esté ejecutándose.'))
      .finally(() => setIsLoading(false))
  }, [])

  const handleCreateTariff = (tariff) => {
    fetch(`${API_URL}/tariffs`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(tariff),
    })
      .then((response) => {
        if (!response.ok) throw new Error('No fue posible guardar la tarifa')
        return response.json()
      })
      .then((createdTariff) => {
        setTariffs((current) => [...current, createdTariff])
        setIsTariffModalOpen(false)
      })
      .catch(() => setError('No se pudo guardar la tarifa. Intenta nuevamente.'))
  }

  const sectionContent = useMemo(() => {
    if (activeSection === 'Tarifas') {
      return (
        <TariffSection
          tariffs={tariffs}
          onAdd={() => setIsTariffModalOpen(true)}
        />
      )
    }
    return <DashboardSection dashboard={dashboard} />
  }, [activeSection, dashboard, tariffs])

  return (
    <div className="app-shell">
      <Sidebar
        activeSection={activeSection}
        isOpen={isSidebarOpen}
        onNavigate={(section) => {
          setActiveSection(section)
          setIsSidebarOpen(false)
        }}
        onClose={() => setIsSidebarOpen(false)}
      />
      <main className="main-content">
        <header className="topbar">
          <button className="icon-button mobile-menu" onClick={() => setIsSidebarOpen(true)} aria-label="Abrir menú">
            <Menu size={21} />
          </button>
          <div className="breadcrumb">
            <span>Administración</span>
            <span className="breadcrumb-separator">/</span>
            <strong>{activeSection}</strong>
          </div>
          <div className="topbar-actions">
            <button className="icon-button" aria-label="Ayuda"><CircleHelp size={20} /></button>
            <div className="avatar">JD</div>
            <div className="user-info">
              <strong>Juan Delgado</strong>
              <span>Administrador</span>
            </div>
            <ChevronDown size={16} className="muted-icon" />
          </div>
        </header>

        <div className="page-content">
          <div className="page-heading">
            <div>
              <p className="eyebrow">Domingo, 15 de noviembre de 2026</p>
              <h1>{activeSection === 'Resumen' ? 'Buenos días, Juan' : activeSection}</h1>
              <p className="page-description">
                {activeSection === 'Resumen'
                  ? 'Aquí tienes el estado actual de tu operación.'
                  : 'Administra la configuración operativa de tu parqueadero.'}
              </p>
            </div>
            <div className="heading-actions">
              <button className="button button--secondary"><ArrowDownToLine size={17} /> Exportar</button>
              {activeSection === 'Tarifas' && (
                <button className="button button--primary" onClick={() => setIsTariffModalOpen(true)}>
                  <Plus size={18} /> Nueva tarifa
                </button>
              )}
            </div>
          </div>

          {error && <div className="error-banner">{error}</div>}
          {isLoading ? <LoadingState /> : sectionContent}
        </div>
      </main>
      {isTariffModalOpen && (
        <TariffModal onClose={() => setIsTariffModalOpen(false)} onSubmit={handleCreateTariff} />
      )}
    </div>
  )
}

function Sidebar({ activeSection, isOpen, onNavigate, onClose }) {
  const menuItems = [
    { label: 'Resumen', icon: LayoutDashboard },
    { label: 'Plazas', icon: ParkingSquare },
    { label: 'Tarifas', icon: Ticket },
    { label: 'Reportes', icon: TrendingUp },
  ]

  return (
    <>
      {isOpen && <button className="sidebar-backdrop" onClick={onClose} aria-label="Cerrar menú" />}
      <aside className={`sidebar ${isOpen ? 'sidebar--open' : ''}`}>
        <div className="brand">
          <div className="brand-mark"><ParkingSquare size={22} strokeWidth={2.5} /></div>
          <span>park<span>ly</span></span>
          <button className="icon-button sidebar-close" onClick={onClose} aria-label="Cerrar menú"><X size={20} /></button>
        </div>
        <div className="workspace-switcher">
          <div className="workspace-icon">P</div>
          <div><strong>Parqueadero Central</strong><span>Sede principal</span></div>
          <ChevronDown size={15} className="workspace-chevron" />
        </div>
        <p className="nav-label">MENÚ PRINCIPAL</p>
        <nav>
          {menuItems.map(({ label, icon: Icon }) => (
            <button
              key={label}
              className={`nav-item ${activeSection === label ? 'nav-item--active' : ''}`}
              onClick={() => onNavigate(label)}
            >
              <Icon size={19} />
              <span>{label}</span>
              {label === 'Plazas' && <span className="nav-badge">12</span>}
            </button>
          ))}
        </nav>
        <p className="nav-label nav-label--bottom">CONFIGURACIÓN</p>
        <nav>
          <button className="nav-item" onClick={() => onNavigate('Configuración')}><Settings size={19} /><span>Configuración</span></button>
          <button className="nav-item" onClick={() => onNavigate('Ayuda')}><CircleHelp size={19} /><span>Centro de ayuda</span></button>
        </nav>
        <div className="sidebar-footer">
          <div className="status-dot" />
          <div><strong>Todos los sistemas operativos</strong><span>Última sincronización: ahora</span></div>
        </div>
        <button className="logout-button"><LogOut size={18} /> Cerrar sesión</button>
      </aside>
    </>
  )
}

function DashboardSection({ dashboard }) {
  if (!dashboard) return null
  const { summary, spaces, activity } = dashboard

  return (
    <>
      <section className="stats-grid">
        <StatCard label="Ocupación actual" value={`${summary.occupancy}%`} detail={`${summary.occupied + summary.reserved} de ${summary.total} plazas en uso`} icon={ParkingSquare} color="blue" trend="+4.2%" />
        <StatCard label="Ingresos de hoy" value={`$${summary.revenue.toLocaleString('es-CO')}`} detail="Comparado con ayer" icon={WalletCards} color="green" trend="+12.8%" />
        <StatCard label="Vehículos activos" value={summary.activeVehicles} detail="En el parqueadero ahora" icon={CarFront} color="orange" />
        <StatCard label="Tiempo promedio" value="1h 42m" detail="Estadía promedio de hoy" icon={Clock3} color="purple" trend="-8.4%" trendDown />
      </section>
      <section className="dashboard-grid">
        <div className="panel occupancy-panel">
          <PanelHeader title="Estado de las plazas" action="Ver todas" />
          <div className="legend">
            {Object.entries(statusLabels).map(([key, label]) => (
              <span key={key}><i className={`legend-dot ${statusClasses[key]}`} />{label}<b>{summary[key.toLowerCase()] ?? 0}</b></span>
            ))}
          </div>
          <div className="parking-map">
            {['A', 'B'].map((zone) => (
              <div className="parking-zone" key={zone}>
                <div className="zone-label">Zona {zone}</div>
                <div className="space-row">
                  {spaces.filter((space) => space.zone === zone).map((space) => (
                    <div className={`parking-space ${statusClasses[space.status]}`} key={space.id} title={`${space.id} · ${statusLabels[space.status]}`}>
                      <span>{space.id}</span>
                      {space.vehicle && <small>{space.vehicle}</small>}
                      {space.status === 'MAINTENANCE' && <Settings size={14} />}
                    </div>
                  ))}
                </div>
              </div>
            ))}
            <div className="map-entrance"><ArrowDownToLine size={15} /> Entrada / Salida</div>
          </div>
        </div>
        <div className="panel activity-panel">
          <PanelHeader title="Actividad reciente" action="Ver historial" />
          <div className="activity-list">
            {activity.map((item) => (
              <div className="activity-item" key={`${item.vehicle}-${item.time}`}>
                <div className={`activity-icon activity-icon--${item.type}`}><ActivityIcon type={item.type} /></div>
                <div className="activity-copy"><strong>{item.action}</strong><span>{item.vehicle}</span></div>
                <time>{item.time}</time>
              </div>
            ))}
          </div>
        </div>
      </section>
      <section className="quick-actions">
        <div><h2>Acciones rápidas</h2><p>Gestiona las tareas más frecuentes de tu operación.</p></div>
        <div className="quick-actions-list">
          <button><Ticket size={19} /><span>Registrar ingreso</span><ArrowUpRight size={16} /></button>
          <button><WalletCards size={19} /><span>Registrar pago</span><ArrowUpRight size={16} /></button>
          <button><TrendingUp size={19} /><span>Ver reporte diario</span><ArrowUpRight size={16} /></button>
        </div>
      </section>
    </>
  )
}

function StatCard({ label, value, detail, icon: Icon, color, trend, trendDown }) {
  return (
    <article className="stat-card">
      <div className={`stat-icon stat-icon--${color}`}><Icon size={20} /></div>
      <div className="stat-label">{label}</div>
      <div className="stat-value">{value}</div>
      <div className="stat-detail">{detail} {trend && <span className={trendDown ? 'trend trend--down' : 'trend'}><TrendingUp size={13} /> {trend}</span>}</div>
    </article>
  )
}

function PanelHeader({ title, action }) {
  return <div className="panel-header"><h2>{title}</h2><button className="text-button">{action} <ArrowUpRight size={15} /></button></div>
}

function ActivityIcon({ type }) {
  if (type === 'payment') return <WalletCards size={16} />
  if (type === 'reservation') return <Ticket size={16} />
  if (type === 'exit') return <ArrowUpRight size={16} />
  return <ArrowDownToLine size={16} />
}

function TariffSection({ tariffs, onAdd }) {
  return (
    <section className="panel tariffs-panel">
      <PanelHeader title="Esquema tarifario" action="Ver historial de cambios" />
      <p className="section-lead">Define cuánto cobrar según el tiempo y horario de permanencia.</p>
      <div className="tariff-table">
        <div className="table-row table-row--header"><span>Nombre</span><span>Valor</span><span>Unidad</span><span>Estado</span><span /></div>
        {tariffs.map((tariff) => (
          <div className="table-row" key={tariff.name}>
            <strong>{tariff.name}</strong>
            <span className="tariff-value">${Number(tariff.value).toLocaleString('es-CO')}</span>
            <span>{tariff.unit}</span>
            <span><i className="active-pill" /> Activa</span>
            <button className="row-action">Editar</button>
          </div>
        ))}
      </div>
      <button className="add-tariff-button" onClick={onAdd}><Plus size={17} /> Añadir una tarifa</button>
    </section>
  )
}

function TariffModal({ onClose, onSubmit }) {
  const [form, setForm] = useState({ name: '', value: '', unit: 'por hora', active: true })
  const update = (field) => (event) => setForm((current) => ({ ...current, [field]: event.target.value }))
  const submit = (event) => {
    event.preventDefault()
    onSubmit({ ...form, value: Number(form.value) })
  }

  return (
    <div className="modal-backdrop" onMouseDown={onClose}>
      <form className="modal" onSubmit={submit} onMouseDown={(event) => event.stopPropagation()}>
        <div className="modal-header"><div><p className="eyebrow">Configuración</p><h2>Nueva tarifa</h2></div><button type="button" className="icon-button" onClick={onClose}><X size={19} /></button></div>
        <label>Nombre<input required value={form.name} onChange={update('name')} placeholder="Ej. Tarifa fin de semana" /></label>
        <label>Valor<input required min="0" type="number" value={form.value} onChange={update('value')} placeholder="0" /></label>
        <label>Unidad<select value={form.unit} onChange={update('unit')}><option>por hora</option><option>hasta 30 min</option><option>tarifa plana</option><option>de 20:00 a 06:00</option></select></label>
        <div className="modal-actions"><button type="button" className="button button--secondary" onClick={onClose}>Cancelar</button><button className="button button--primary">Guardar tarifa</button></div>
      </form>
    </div>
  )
}

function LoadingState() {
  return <div className="loading-state"><div className="spinner" /><span>Cargando información operativa...</span></div>
}

export default App
