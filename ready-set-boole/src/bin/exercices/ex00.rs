pub fn adder(a: u32, b: u32) -> u32 {
    let mut a = a;
    let mut b = b;
    let mut tmp;
    while b != 0 {
        tmp = a ^ b;
        b = (a & b) << 1;
        a = tmp;
    }
    return a;
}
