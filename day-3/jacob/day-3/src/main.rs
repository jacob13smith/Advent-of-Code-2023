use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn main() {

    let offset_pairs = [(-1, -1,), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1)];

    if let Ok(lines) = read_lines("./input.txt") {
        let mut sum = 0;

        let mut matrix: Vec<Vec<char>> = Vec::new();

        for line in lines {
            if let Ok(line_str) = line {
                matrix.push(line_str.chars().collect());
            }
        }

        for row in 0i32..matrix.len() as i32 {
            for col in 0..matrix[row as usize].len() as i32 {
                let curr = matrix[row as usize][col as usize];
                if curr == '*' {
                    // Symbol found, check for numbers around it
                    let mut acc_vec : Vec<String> = Vec::new();
                    for pair in offset_pairs {
                        if row + pair.0 >= 0 && row + pair.0 < matrix.len() as i32 && col + pair.1 >= 0 && col + pair.1 < matrix[row as usize].len() as i32{
                            // Check the spot pair
                            let curr_check =  matrix[(row as i32 + pair.0) as usize][(col as i32 + pair.1) as usize];
                            if curr_check.is_numeric() {
                                // Found a number
                                let mut acc = Vec::new();
                                acc.push(curr_check.clone());
                                matrix[(row as i32 + pair.0) as usize][(col as i32 + pair.1) as usize] = '.';

                                let mut left_move = -1;
                                let mut check_left = if col as i32 + pair.1 + left_move >= 0 {matrix[(row as i32 + pair.0) as usize][(col as i32 + pair.1 + left_move) as usize]} else {'.'};
                                while check_left.is_numeric() {
                                    acc.insert(0, check_left);
                                    matrix[(row as i32 + pair.0) as usize][(col as i32 + pair.1 + left_move) as usize] = '.';

                                    left_move -= 1;
                                    check_left = if col as i32 + pair.1 + left_move >= 0 {matrix[(row as i32 + pair.0) as usize][(col as i32 + pair.1 + left_move) as usize]} else {'.'}
                                }

                                let mut right_move = 1;
                                let mut check_right = if col + right_move + pair.1 < matrix[(row as i32 + pair.0) as usize].len() as i32 {matrix[(row as i32 + pair.0) as usize][(col as i32 + pair.1 + right_move) as usize]} else {'.'};
                                while check_right.is_numeric() {
                                    acc.push(check_right);
                                    matrix[(row as i32 + pair.0) as usize][(col as i32 + pair.1 + right_move) as usize] = '.';

                                    right_move += 1;
                                    check_right = if col + right_move + pair.1 < matrix[(row as i32 + pair.0) as usize].len() as i32 {matrix[(row as i32 + pair.0) as usize][(col as i32 + pair.1 + right_move) as usize]} else {'.'}
                                }

                                let acc_string = String::from_iter(acc);
                                //println!("{}", acc_string);
                                acc_vec.push(acc_string);
                            }
                        } 
                    }
                    if acc_vec.len() == 2 {
                        println!("{} * {}", acc_vec[0], acc_vec[1]);
                        sum += acc_vec[0].parse::<i32>().unwrap() * acc_vec[1].parse::<i32>().unwrap();
                    }
                }
            }
        }
       println!("{}", sum);
    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}