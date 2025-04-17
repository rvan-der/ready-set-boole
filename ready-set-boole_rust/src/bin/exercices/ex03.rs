#[allow(dead_code)]
#[derive(Debug)]
enum BoolAlgToken<'a> {
	Op(BoolAlgOperator<'a>),
	Scalar(bool),
	Var(Option<bool>)
}

#[allow(dead_code)]
#[derive(Debug)]
enum BoolAlgOperator<'a> {
	// Negation(Option<&'a mut BoolAlgToken<'a>>),
	And { left: &'a mut BoolAlgToken<'a>, right: &'a mut BoolAlgToken<'a> },
	// Or{left: &'a mut BoolAlgToken<'a>, right: &'a mut BoolAlgToken<'a>},
	// Xor{left: &'a mut BoolAlgToken<'a>, right: &'a mut BoolAlgToken<'a>},
	// Implication{left: &'a mut BoolAlgToken<'a>, right: &'a mut BoolAlgToken<'a>},
	// Equivalence{left: &'a mut BoolAlgToken<'a>, right: &'a mut BoolAlgToken<'a>}
}

impl BoolAlgOperator {
	fn setLeft
}


// fn eval_formula(formula: &str) -> bool {
// 	let ast;
// 	let stack: Vec<BoolAlgToken> = Vec::new();

// 	for c: char in formla.chars() {

// 	}
// }
fn main() {
	let l = &mut BoolAlgToken::Scalar(true);
	let r = &mut BoolAlgToken::Scalar(true);
	let test = BoolAlgToken::Op(BoolAlgOperator::And{left: l, right: r});
	println!("{:?}", test);
}