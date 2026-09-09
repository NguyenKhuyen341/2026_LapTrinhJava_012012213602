/**
 * PlanbookAI Main Front-End Interactive Script
 */

document.addEventListener('DOMContentLoaded', () => {
  initNavigation();
  renderDashboard();
  renderGiaoAnTable();
  renderDeThiTable();
  renderHocSinhTable();
  initModals();
  initAiGenerator();
});

// Navigation Handling
function initNavigation() {
  const navItems = document.querySelectorAll('.nav-item');
  const tabViews = document.querySelectorAll('.tab-view');

  navItems.forEach(item => {
    item.addEventListener('click', () => {
      const targetTab = item.getAttribute('data-tab');
      if (!targetTab) return;

      navItems.forEach(n => n.classList.remove('active'));
      tabViews.forEach(v => v.classList.remove('active'));

      item.classList.add('active');
      const activeView = document.getElementById(`tab-${targetTab}`);
      if (activeView) activeView.classList.add('active');
    });
  });
}

// Render Dashboard Metrics
function renderDashboard() {
  const stats = mockData.stats;
  document.getElementById('stat-giaoan').textContent = stats.totalGiaoAn;
  document.getElementById('stat-dethi').textContent = stats.totalDeThi;
  document.getElementById('stat-hocsinh').textContent = stats.totalHocSinh;
  document.getElementById('stat-ai').textContent = stats.aiGenerated;
}

// Render Lesson Plans Table
function renderGiaoAnTable(filterText = '') {
  const tbody = document.getElementById('giaoan-tbody');
  if (!tbody) return;

  const filtered = mockData.giaoAnList.filter(item =>
    item.tenBaiHoc.toLowerCase().includes(filterText.toLowerCase()) ||
    item.monHoc.toLowerCase().includes(filterText.toLowerCase())
  );

  tbody.innerHTML = filtered.map(item => `
    <tr>
      <td><strong>#${item.id}</strong></td>
      <td><strong>${escapeHtml(item.tenBaiHoc)}</strong></td>
      <td>${escapeHtml(item.monHoc)}</td>
      <td>${escapeHtml(item.khoiLop)}</td>
      <td><span class="badge ${getStatusBadgeClass(item.trangThai)}">${item.trangThai}</span></td>
      <td>${item.ngayTao}</td>
      <td>
        <button class="btn btn-secondary btn-sm" onclick="viewGiaoAnDetail(${item.id})"> Xem </button>
        <button class="btn btn-secondary btn-sm" onclick="viewGiaoAnDetail(${item.id})"> Xóa </button>
        <button class="btn btn-secondary btn-sm" onclick="viewGiaoAnDetail(${item.id})"> Sửa </button>
      </td>
    </tr>
  `).join('');
}

// Render Exams Table
function renderDeThiTable() {
  const tbody = document.getElementById('dethi-tbody');
  if (!tbody) return;

  tbody.innerHTML = mockData.deThiList.map(item => `
    <tr>
      <td><strong>#${item.id}</strong></td>
      <td><strong>${escapeHtml(item.tieuDe)}</strong></td>
      <td>${item.thoiGian}</td>
      <td>${item.soCauHoi} câu</td>
      <td><span class="badge ${getStatusBadgeClass(item.trangThai)}">${item.trangThai}</span></td>
      <td>${item.ngayTao}</td>
      <td>
        <button class="btn btn-secondary btn-sm" onclick="alert('Đã tải cấu trúc đề thi #${item.id}')">📥 Tải về</button>
      </td>
    </tr>
  `).join('');
}

// Render Students Table
function renderHocSinhTable() {
  const tbody = document.getElementById('hocsinh-tbody');
  if (!tbody) return;

  tbody.innerHTML = mockData.hocSinhList.map(item => `
    <tr>
      <td><code>${item.maHS}</code></td>
      <td><strong>${escapeHtml(item.hoTen)}</strong></td>
      <td>${item.lop}</td>
      <td><strong style="color:#38bdf8;">${item.diemTB}</strong></td>
      <td><span class="badge badge-success">${item.xepLoai}</span></td>
    </tr>
  `).join('');
}

// AI Generator Demo
function initAiGenerator() {
  const btnGenerate = document.getElementById('btn-generate-ai');
  const promptInput = document.getElementById('ai-prompt-input');
  const outputBox = document.getElementById('ai-output-box');

  if (btnGenerate && promptInput && outputBox) {
    btnGenerate.addEventListener('click', () => {
      const topic = promptInput.value.trim() || "Cấu trúc vòng lặp trong Java";
      btnGenerate.disabled = true;
      btnGenerate.innerHTML = '⏳ AI đang suy nghĩ...';
      outputBox.textContent = 'Đang kết nối tới mô hình AI PlanbookAI... Vui lòng chờ trong giây lát.';

      setTimeout(() => {
        outputBox.textContent = `[PLANBOOK AI GENERATED RESULT]\n\n`
          + `📚 TIÊU ĐỀ: Kế hoạch bài giảng - ${topic}\n`
          + `🎯 MỤC TIÊU BÀI HỌC:\n`
          + ` 1. Hiểu rõ cú pháp và luồng thực thi của ${topic}.\n`
          + ` 2. Vận dụng giải quyết bài toán tính tổng dãy số và duyệt mảng.\n\n`
          + `📝 CÂU HỎI ĐỀ XUẤT TỰ ĐỘNG:\n`
          + ` Q1: Hãy phân biệt sự khác nhau giữa vòng lặp while và do-while?\n`
          + ` Q2: Viết chương trình in ra danh sách các số nguyên tố nhỏ hơn 100.`;

        btnGenerate.disabled = false;
        btnGenerate.innerHTML = '✨ AI Sinh Giáo Án Tự Động';
      }, 1200);
    });
  }
}

// Modals Handling
function initModals() {
  const authModal = document.getElementById('auth-modal');
  const btnOpenAuth = document.getElementById('btn-open-auth');
  const btnCloseAuth = document.getElementById('btn-close-auth');

  if (btnOpenAuth && authModal) {
    btnOpenAuth.addEventListener('click', () => authModal.classList.add('active'));
  }
  if (btnCloseAuth && authModal) {
    btnCloseAuth.addEventListener('click', () => authModal.classList.remove('active'));
  }

  // Filter input event
  const searchGiaoAn = document.getElementById('search-giaoan');
  if (searchGiaoAn) {
    searchGiaoAn.addEventListener('input', (e) => {
      renderGiaoAnTable(e.target.value);
    });
  }
}

function viewGiaoAnDetail(id) {
  const found = mockData.giaoAnList.find(g => g.id === id);
  if (found) {
    alert(`Chi tiết Giáo án:\n\nTên bài: ${found.tenBaiHoc}\nMôn học: ${found.monHoc}\nTác giả: ${found.tacGia}\nTrạng thái: ${found.trangThai}`);
  }
}

function getStatusBadgeClass(status) {
  switch (status) {
    case 'PUBLISHED': case 'APPROVED': return 'badge-success';
    case 'DRAFT': return 'badge-warning';
    case 'ARCHIVED': return 'badge-danger';
    default: return 'badge-info';
  }
}

function escapeHtml(str) {
  return str.replace(/[&<>'"]/g,
    tag => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', "'": '&#39;', '"': '&quot;' }[tag] || tag)
  );
}
