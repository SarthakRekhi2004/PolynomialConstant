# Polynomial Constant Finder (Java)

This project computes the **constant term `c`** of a polynomial using **Lagrange interpolation** from given JSON inputs.  
The constant term `c` corresponds to the value of the polynomial at `x = 0`, i.e., `f(0)`.

---

## 🚀 Features
- Decodes `y` values from any base (2–36).
- Works with JSON inputs that define `(x, y)` points.
- Uses **exact arithmetic (`BigInteger`)** to avoid rounding errors.
- Supports polynomials of any degree (`k - 1` where `k` is defined in JSON).
- Example JSON files included (`file1.json`, `file2.json`).

---

## 📂 Project Structure
