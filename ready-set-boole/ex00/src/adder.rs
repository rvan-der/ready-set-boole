pub fn adder(a: u32, b: u32) -> u32 {
    let mut a = a;
    let mut b = b;
    let mut result;
    loop {
        result = a ^ b;
        b = (a & b) << 1;
        if b == 0 {
            break result;
        }
        a = result;
    }
}